package com.bitcoin.chart.domain.common

abstract class BaseMapper<Input, Output> {
    abstract fun convert(input: Input): Output
}