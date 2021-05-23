package com.bitcoin.chart.domain.exception

class EssentialParamMissingException(missingParams: String, rawObject: Any) :
    RuntimeException("The params: $missingParams are missing in received object: $rawObject")
