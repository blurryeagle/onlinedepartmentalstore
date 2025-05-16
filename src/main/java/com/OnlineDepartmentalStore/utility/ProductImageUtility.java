package com.OnlineDepartmentalStore.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductImageUtility {

    // Logger instance for logging messages
    private static final Logger LOGGER = Logger.getLogger(ProductImageUtility.class.getName());

    // Folder inside webapp where product images will be saved
    private static final String IMAGE_FOLDER = "product_images";

    // The absolute path to save the image on your system
    private static final String BASE_PATH = "C:\\Users\\drbis\\eclipse-workspace\\OnlineDepartmentalStore\\src\\main\\webapp\\";

    public static String saveProductImage(Part part, ServletContext context) throws IOException {
        if (part == null || part.getSize() == 0) {
            LOGGER.log(Level.WARNING, "No file uploaded or the uploaded file size is zero. [File: {0}]", part != null ? part.getSubmittedFileName() : "N/A");
            return null;
        }

        // Generate a unique filename to avoid overwriting
        String fileName = UUID.randomUUID().toString() + "_" +
                          Paths.get(part.getSubmittedFileName()).getFileName().toString();

        // Get the absolute path to the folder where images should be stored inside webapp (not the tmp directory)
        String uploadPath = BASE_PATH + IMAGE_FOLDER;

        // Save the file to the directory
        String fullImagePath = uploadPath + File.separator + fileName;
        try {
            part.write(fullImagePath);
            LOGGER.log(Level.INFO, "File uploaded successfully. [File: {0}] [Saved to: {1}]", new Object[]{part.getSubmittedFileName(), fullImagePath});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while saving the image file. [Error: {0}] [File: {1}]", new Object[]{e.getMessage(), part.getSubmittedFileName()});
            return null;
        }

        // Return the relative path to store in the database
        return IMAGE_FOLDER + "/" + fileName;
    }
}
