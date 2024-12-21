

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Variable to store the selected image URI
    private var selectedImageUri: Uri? = null

    // Register the ActivityResultLauncher
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // This callback is triggered after the user selects an image
            if (uri != null) {
                selectedImageUri = uri // Store the image URI
                // You can now use the URI, e.g., display it in an ImageView
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example: Open the gallery when a button is clicked
        val openGalleryButton = findViewById<Button>(R.id.openGalleryButton)
        openGalleryButton.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        // Launch the gallery to pick an image
        pickImageLauncher.launch("image/*") // MIME type for images
    }
}
