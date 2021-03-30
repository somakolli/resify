package de.freshspark.resify.models

val defaultPersonalInformation = """
  {
    "type": "object",
    "properties": {
      "first-name": {
        "type": "string"
      },
      "last-name": {
        "type": "string"
      },
      "email": {
        "type": "string"
      },
      "phone-number": {
        "type": "string"
      }
    },
    "required": ["first-name", "last-name", "email", "phone-number"]
  }
""".trimIndent().trimMargin().replace("\n", "").replace(" ", "")
