package com.example.data.news

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class NewsMapJsonDeserializer : JsonDeserializer<NewsMap> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NewsMap = NewsMap(
        json.asJsonObject.get("author").asString,
        json.asJsonObject.get("title").asString,
        json.asJsonObject.get("description").asString,
        json.asJsonObject.get("url").asString,
        json.asJsonObject.get("urlToImage").asString,
        json.asJsonObject.get("publishedAt").asString )
}
