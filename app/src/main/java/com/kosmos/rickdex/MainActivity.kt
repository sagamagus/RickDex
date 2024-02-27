package com.kosmos.rickdex

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kosmos.rickdex.ui.theme.RickDexTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var characters = ArrayList<RickCharacter>()
class MainActivity : ComponentActivity() {

    //var characters = ArrayList<RickCharacter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postId = 1 // Replace with the desired post ID
        val call = RickApiClient.apiService.getCharsbyPage(postId)

        call.enqueue(object : Callback<RickResponse> {
            override fun onResponse(call: Call<RickResponse>, response: Response<RickResponse>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    characters.addAll(post!!.results)
                    Log.e ("name: ", post!!.results[0].name)
                    // Handle the retrieved post data
                    setContent {
                        RickDexTheme {
                            // A surface container using the 'background' color from the theme
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                App()
                            }
                        }
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<RickResponse>, t: Throwable) {
                // Handle failure
            }
        })


    }

}

@Composable
fun App() {
    val selectedContact = remember { mutableStateOf<RickCharacter?>(null) }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLandscape) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    CharacterListScreen(onCharacterClick = { character ->
                        selectedContact.value = character
                    })
                }
                Box(modifier = Modifier.weight(1f)) {
                    selectedContact.value?.let { character ->
                        CharacterDetailScreen(character = character, onCharacterClick = { character ->
                            selectedContact.value = null
                        })
                    }
                }
            }
        } else {
            if (selectedContact.value == null) {
                CharacterListScreen(onCharacterClick = { character ->
                    selectedContact.value = character
                })
            } else {
                CharacterDetailScreen(character = selectedContact.value!!, onCharacterClick = { character ->
                    selectedContact.value = null
                })
            }
        }
    }
}

@Composable
fun CharacterListScreen(onCharacterClick: (RickCharacter) -> Unit) {
    LazyColumn {
        items(characters) { character ->
            CharacterItem(character = character, onCharacterClick = onCharacterClick)
        }
    }
}

@Composable
fun CharacterItem(character: RickCharacter, onCharacterClick: (RickCharacter) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.clickable { onCharacterClick(character) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(character.image),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = character.name, style = MaterialTheme.typography.h6)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Button(onClick = { onCharacterClick(character) }) {
                Text("Ver detalle")
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(character: RickCharacter, onCharacterClick: (RickCharacter) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(character.image),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = character.status, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = character.species, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = character.type, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = character.gender, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = character.origin.name, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = character.location.name, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = { onCharacterClick(character) }) {
            Text("Ocultar detalle")
        }
    }
}