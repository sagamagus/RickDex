package com.kosmos.rickdex

import com.google.gson.annotations.SerializedName

data class Info (@SerializedName("count") var count:Int,
                 @SerializedName("pages") var pages:Int,
                 @SerializedName("next") var next:String,
                 @SerializedName("prev") var species:String)  //var images: List<String>)

