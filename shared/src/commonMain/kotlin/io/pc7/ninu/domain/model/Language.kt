package io.pc7.ninu.domain.model

enum class Language {
    English,
    Slovenia;

    fun getLanguageCode(): String{
        return when(this){
            English -> "en"
            Slovenia -> "sl"
        }
    }

    companion object{
        fun toLanguage(languageCode: String): Language{
            return when(languageCode){
                "sl" -> Slovenia
                "en" -> English
                else -> English
            }
        }
    }

}