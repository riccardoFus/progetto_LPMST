package com.example.shelfy.data.remote.responses

data class Item(
    val accessInfo: AccessInfo = AccessInfo(),
    val etag: String = "",
    val id: String = "",
    val kind: String = "",
    val saleInfo: SaleInfo = SaleInfo(),
    val searchInfo: SearchInfo = SearchInfo(),
    val selfLink: String = "",
    val volumeInfo: VolumeInfo = VolumeInfo()
)