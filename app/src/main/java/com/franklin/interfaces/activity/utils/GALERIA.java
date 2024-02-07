package com.franklin.interfaces.activity.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GALERIA {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static File archivo_imagen;

    public static void abrirGaleria(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data, ImageView foto) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // La URI de la imagen seleccionada
            Uri selectedImageUri = data.getData();

            // Decodificar la URI en un Bitmap
            try {
                InputStream inputStream = foto.getContext().getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                Log.d("GALERIA", "antes");
                foto.setImageBitmap(bitmap);
                Log.d("GALERIA", "después");


                // Obtener el archivo correspondiente a la URI y asignarlo a la variable estática
                archivo_imagen = new File(selectedImageUri.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Método getter para obtener el archivo de imagen seleccionado
    public static File getArchivoImagen() {
        return archivo_imagen;
    }
}
