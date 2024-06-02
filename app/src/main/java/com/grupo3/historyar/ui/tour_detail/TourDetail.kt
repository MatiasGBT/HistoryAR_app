package com.grupo3.historyar.ui.tour_detail

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.grupo3.historyar.R
import com.grupo3.historyar.adapters.CommentListAdapter
import com.grupo3.historyar.adapters.MiniPointAdapter
import com.grupo3.historyar.databinding.FragmentTourDetailBinding
import com.grupo3.historyar.models.Qualification
import com.grupo3.historyar.models.PointOfInterest
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.models.User
import com.grupo3.historyar.ui.view_models.*
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

const val ID_BUNDLE = "id_bundle"
class TourDetail : Fragment() {
    private val tourViewModel: TourViewModel by activityViewModels()
    private val qualificationViewModel: QualificationViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private var id: String? = null
    private var _binding: FragmentTourDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var miniPointAdapter: MiniPointAdapter
    private lateinit var commentAdapter: CommentListAdapter
    private lateinit var tour: Tour
    private lateinit var user: User
    private lateinit var qualifications: MutableList<Qualification>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        //TODO: Obtener calificación del usuario y pintar estrellitas en base a dicha calificación
        observeTour()
        observeCommentList()
        observeUser()
        tourViewModel.getTour(id!!)
        qualificationViewModel.getQualificationsByTourId(id!!)
        userViewModel.getUserLoggedIn()
        initPlayBtn()
        initQualificationBtns()
    }

    private fun observeTour() {
        tourViewModel.tourModel.observe(viewLifecycleOwner) {
            clearStarIcons()
            tour = it
            binding.tvTourName.text = tour.name
            binding.tvTourDescription.text = tour.description
            Picasso.get().load(tour.image).into(binding.ivTourImage)
            initPointsOfInterestAdapter(it.points)
        }
    }

    private fun observeCommentList() {
        qualificationViewModel.commentsAreLoading.observe(viewLifecycleOwner) {
            binding.pbComments.isVisible = it
            binding.rvComments.isVisible = !it
        }
        qualificationViewModel.commentsModel.observe(viewLifecycleOwner) {
            qualifications = it.toMutableList()
            val qualificationAverage = qualifications.map { qualification -> qualification.score }.average()
            if (!qualificationAverage.isNaN())
                paintStarIcons(qualificationAverage.roundToInt())
            initCommentsAdapter(qualifications)
        }
    }

    private fun observeUser() {
        userViewModel.userModel.observe(viewLifecycleOwner) {
            user = it
        }
    }

    private fun initPointsOfInterestAdapter(pointList: List<PointOfInterest>) {
        miniPointAdapter = MiniPointAdapter()
        binding.rvPoints.setHasFixedSize(true)
        binding.rvPoints.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPoints.adapter = miniPointAdapter
        miniPointAdapter.updateList(pointList)
    }

    private fun initCommentsAdapter(commentList: List<Qualification>) {
        commentAdapter = CommentListAdapter()
        binding.rvComments.setHasFixedSize(true)
        binding.rvComments.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvComments.adapter = commentAdapter
        commentAdapter.updateList(commentList)
    }

    private fun initPlayBtn() {
        binding.ivPlay.setOnClickListener {
            val bundle = bundleOf(ID_BUNDLE to id)
            findNavController().navigate(R.id.action_tourDetail_to_tourPlayFragment, bundle)
        }
    }

    private fun initQualificationBtns() {
        binding.ivStarIcon1.setOnClickListener {
            showQualificationDialog(1)
        }
        binding.ivStarIcon2.setOnClickListener {
            showQualificationDialog(2)
        }
        binding.ivStarIcon3.setOnClickListener {
            showQualificationDialog(3)
        }
        binding.ivStarIcon4.setOnClickListener {
            showQualificationDialog(4)
        }
        binding.ivStarIcon5.setOnClickListener {
            showQualificationDialog(5)
        }
    }

    private fun showQualificationDialog(qualification: Int) {
        val dialog = getQualificationDialog()
        setQualificationDialogText(dialog, qualification)
        initQualificationDialogBtnListener(dialog, qualification)
        dialog.show()
    }

    private fun getQualificationDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.qualification_dialog)
        return dialog
    }

    private fun setQualificationDialogText(dialog: Dialog, qualification: Int) {
        val qualificationText: TextView = dialog.findViewById(R.id.tvDialogQualification)
        qualificationText.text = qualification.toString()
    }

    private fun initQualificationDialogBtnListener(dialog: Dialog, qualification: Int) {
        val qualificationBtn: Button = dialog.findViewById(R.id.btnDialogQualificationSend)
        qualificationBtn.setOnClickListener {
            val comment = getQualificationDialogComment(dialog)
            if (comment.isNotBlank() && comment.isNotEmpty()) {
                clearStarIcons()
                paintStarIcons(qualification)
                dialog.dismiss()
                createQualification(comment, qualification)
            } else {
                Toast.makeText(requireActivity(), "Deja un comentario para calificar este recorrido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getQualificationDialogComment(dialog: Dialog): String {
        val comment: TextInputEditText = dialog.findViewById(R.id.tietDialogQualification)
        return comment.text.toString()
    }

    private fun clearStarIcons() {
        binding.ivStarIcon1.setImageResource(R.drawable.ic_round_star_border_24)
        binding.ivStarIcon2.setImageResource(R.drawable.ic_round_star_border_24)
        binding.ivStarIcon3.setImageResource(R.drawable.ic_round_star_border_24)
        binding.ivStarIcon4.setImageResource(R.drawable.ic_round_star_border_24)
        binding.ivStarIcon5.setImageResource(R.drawable.ic_round_star_border_24)
    }

    private fun paintStarIcons(qualification: Int) {
        if (qualification >= 1)
            binding.ivStarIcon1.setImageResource(R.drawable.ic_round_star_24)
        if (qualification >= 2)
            binding.ivStarIcon2.setImageResource(R.drawable.ic_round_star_24)
        if (qualification >= 3)
            binding.ivStarIcon3.setImageResource(R.drawable.ic_round_star_24)
        if (qualification >= 4)
            binding.ivStarIcon4.setImageResource(R.drawable.ic_round_star_24)
        if (qualification == 5)
            binding.ivStarIcon5.setImageResource(R.drawable.ic_round_star_24)
    }

    private fun createQualification(comment: String, qualification: Int) {
        val qualification = Qualification(
            user = user,
            tour = tour.id,
            comment = comment,
            score = qualification
        )
        qualifications = (listOf(qualification) + qualifications).toMutableList()
        commentAdapter.updateListByPosition(qualifications)
        //TODO: qualificationViewModel.deleteQualification if exists
        qualificationViewModel.saveQualification(qualification)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}