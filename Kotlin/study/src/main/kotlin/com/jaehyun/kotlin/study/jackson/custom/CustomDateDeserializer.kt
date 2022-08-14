package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class CustomDateDeserializer(t: Class<Date>? = null) : StdDeserializer<Date>(t) {
    companion object {
        private val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    }

    @Throws(IOException::class, ParseException::class)
    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): Date {
        val date = jsonParser.text

        return formatter.parse(date)
    }
}

class CustomDateSerializer(t: Class<Date>? = null) : StdSerializer<Date>(t) {
    companion object {
        private val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    }

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(
        value: Date,
        generator: JsonGenerator,
        provider: SerializerProvider
    ) {
        generator.writeString(formatter.format(value))
    }
}