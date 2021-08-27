package com.udacity.politcalpreparedness.representative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.udacity.politcalpreparedness.R
import com.udacity.politcalpreparedness.databinding.FragmentElectionBinding
import com.udacity.politcalpreparedness.databinding.FragmentRepresentativeBinding

class RepresentativeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Inflating and Returning the View with DataBindingUtil
        val binding: FragmentRepresentativeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_representative, container, false)
        return binding.root
    }
}