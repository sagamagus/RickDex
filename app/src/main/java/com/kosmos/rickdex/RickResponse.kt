package com.kosmos.rickdex

import com.google.gson.annotations.SerializedName

data class RickResponse (@SerializedName("info") var info:Info,
                     @SerializedName("results") var results:List<RickCharacter>)

