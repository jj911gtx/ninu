package io.pc7.ninu.presentation.components.util

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

fun getPathDataFromSvg(context: Context, svgResId: Int): String? {
    
    try {
        val parser: XmlPullParser = context.resources.getXml(svgResId)
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
                return parser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData")
            }
            eventType = parser.next()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    
    return null
}