package eu.darken.myperm.permissions.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.darken.myperm.R
import eu.darken.myperm.common.lists.differ.update
import eu.darken.myperm.common.lists.setupDefaults
import eu.darken.myperm.common.uix.Fragment3
import eu.darken.myperm.common.viewbinding.viewBinding
import eu.darken.myperm.databinding.PermissionsDetailsFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class PermissionDetailsFragment : Fragment3(R.layout.permissions_details_fragment) {

    override val vm: PermissionDetailsFragmentVM by viewModels()
    override val ui: PermissionsDetailsFragmentBinding by viewBinding()

    @Inject lateinit var detailsAdapter: PermissionDetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ui.list.setupDefaults(detailsAdapter)
        ui.toolbar.setupWithNavController(findNavController())

        vm.details.observe2(ui) { details ->
            toolbar.subtitle = details.label

            detailsAdapter.update(details.items)
            list.isVisible = true
            loadingContainer.isGone = details.perm != null
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
