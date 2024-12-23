package io.pc7.ninu.presentation.main.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import io.pc7.ninu.domain.model.perfume.Fragrance
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ArraySerializer
import kotlinx.serialization.json.Json


object CustomNavType {

    @OptIn(ExperimentalSerializationApi::class)
    val fragranceArrayType = object : NavType<Array<Fragrance>>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Array<Fragrance>? {
            val json = bundle.getString(key) ?: return null
            return Json.decodeFromString<Array<Fragrance>>(json)
        }

        override fun parseValue(value: String): Array<Fragrance> {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Array<Fragrance>): String {
            return Uri.encode(Json.encodeToString(ArraySerializer(Fragrance.serializer()), value))
        }

        override fun put(bundle: Bundle, key: String, value: Array<Fragrance>) {
            bundle.putString(key, Json.encodeToString(ArraySerializer(Fragrance.serializer()), value))
        }
    }
}
