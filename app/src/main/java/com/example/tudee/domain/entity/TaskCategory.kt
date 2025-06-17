package com.example.tudee.domain.entity

data class TaskCategory(
    val id: Long,
    val title:String,
    val isPredefined: Boolean,
    val image: String,
)
//fun tudyImage(
//    imageRes: ImageResource
//)
//{
//    TaskCategory(
//        id = 0L,
//        title = "Tudee",
//        isPredefined = true,
//        image = ImageResource.DrawableImage(R.drawable.ic_tudee_logo)
//    )
//    when(imageRes){
//        is ImageResource.DrawableImage -> "drawable/${imageRes.resId}"
//        is ImageResource.UserInput -> imageRes.url
//    }
//}
//sealed class ImageResource {
//    data class DrawableImage(val resId: Int) : ImageResource()
//    data class UserInput(val url: String) : ImageResource()
//}