package de.freshspark.resify.models

import java.time.LocalDate
import javax.json.bind.annotation.JsonbDateFormat

class DateRange (
    @JsonbDateFormat
    var startDate: LocalDate? = null,
    @JsonbDateFormat
    var endDate: LocalDate? = null 
)
