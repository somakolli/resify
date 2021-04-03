package de.freshspark.resify.models
fun String.minimizeJson(): String {
  return trimIndent().trimMargin().replace("\n", "").replace(" ", "")
}

val defaultPersonalInformation = """
{
  "type": "object",
  "properties": {
    "first-name": {
      "type": "string",
      "minLength": 2,
      "maxLength": 20
    },
    "last-name": {
      "type": "string",
      "minLength": 2,
      "maxLength": 20
    },
    "email": {
      "type": "string",
      "format": "email"
    },
    "phone-number": {
      "type": "string"
    }
  },
  "required": ["first-name", "last-name", "email", "phone-number"]
}
""".minimizeJson()