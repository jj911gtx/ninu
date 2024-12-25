package io.pc7.ninu.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.NINUTheme

enum class InfoBracketType{
    NEW,
    TIP,
    BLOG;
}





@Composable
fun InfoBracket(
    modifier: Modifier = Modifier,
    type: InfoBracketType,
    smallTitle: String? = null,
    title: String? = null,
    description: String? = null,
    readMore: Boolean = false,
    pager: PagerState? = null
) {

    val gradientBrush = remember {
        Brush.linearGradient(
            0f to Color.Transparent,
            0.238f to Color.Transparent,
            0.6597f to colorScheme.black.copy(alpha = 0.4f),
            1f to colorScheme.black.copy(alpha = 0.9f),
            start = Offset(0f, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
    ) {

        Image(painter = painterResource(id = R.drawable.infobracketimage), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        )


        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(
                    when (type) {
                        InfoBracketType.NEW -> colorScheme.secondaryDark1
                        InfoBracketType.TIP -> colorScheme.primaryMedium
                        InfoBracketType.BLOG -> colorScheme.secondaryDark1
                    }
                )

                ,
            contentAlignment = Alignment.Center

        ) {

            Text(
                text =
                when(type){
                    InfoBracketType.NEW -> "new"
                    InfoBracketType.TIP -> "tips"
                    InfoBracketType.BLOG -> "blog"
                }.uppercase(),
                color = colorScheme.white,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            )

        }



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {


            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.align(Alignment.BottomStart)
                    .padding(bottom = 30.dp)
            ) {
                smallTitle?.let {
                    Text(
                        text = smallTitle,
                        color = colorScheme.white,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
                title?.let {
                    Text(
                        text = title,
                        color = colorScheme.white,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                description?.let {
                    Text(
                        text = description,
                        color = colorScheme.white,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
//                if(readMore){
//                    Button(
//                        onClick = { /*TODO*/ },
//                        shape = RoundedCornerShape(5.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//
//                    ) {
//                        Text(
//                            text = "Read more",
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.labelMedium,
//                            modifier = Modifier
//                                .padding(vertical = 5.dp)
//                                .fillMaxWidth()
//
//                        )
//                    }
//                }

            }
        }
    }
}


fun Modifier.slidingLineTransition(pagerState: PagerState, distance: Float) =
    graphicsLayer {
        val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
        translationX = scrollPosition * distance
    }

@Preview(widthDp = 300, heightDp = 300)
@Composable
private fun InfoBracketPreview() {
    NINUTheme {
        val pager = rememberPagerState(initialPage = 0) {
            3
        }
        InfoBracket(
            type = InfoBracketType.NEW,
            smallTitle = "NINU Tips & Tricks",
            title = "How To Properly Apply Fragrance",
            description = "Create your own premium scent, made from highest quality ingredients",
            readMore = false,
            pager = pager
        )
    }

}