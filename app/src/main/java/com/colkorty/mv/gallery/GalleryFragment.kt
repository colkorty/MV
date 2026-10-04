package com.colkorty.mv.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.colkorty.mv.R
import java.io.File

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gallery: RecyclerView = view.findViewById(R.id.recyclerViewGallery)

        gallery.layoutManager = GridLayoutManager(requireContext(),
            7,
            GridLayoutManager.HORIZONTAL,
            false)

        val folderPath = File(requireContext().getExternalFilesDir(null), "rand_images").absolutePath
        val imagePaths = getImagesFromFolder(folderPath).map { it.absolutePath }

        gallery.adapter = GalleryAdapter(imagePaths) {
            refreshGallery(gallery, requireContext())
        }
    }

    override fun onResume() {
        super.onResume()

        val gallery: RecyclerView = view?.findViewById(R.id.recyclerViewGallery) ?: return

        refreshGallery(gallery, requireContext())
    }
}