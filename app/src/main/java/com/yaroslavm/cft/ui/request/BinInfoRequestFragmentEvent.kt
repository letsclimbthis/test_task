package com.yaroslavm.cft.ui.request

sealed class BinInfoRequestFragmentEvent {
    object ErrorResponseConsumed: BinInfoRequestFragmentEvent()
}

