package vn.propzy.customspinner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import vn.propzy.customspinner.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.spinner.adapter = MyCustom()

    }

    class MyCustom : BaseAdapter() {
        val count1: Int = 10
        val count2: Int = 20

        var myCount: Int = count1

        override fun getCount(): Int {
            return myCount;
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = TextView(parent?.context)
            v.text = "Click to open a Custom a DropDown"
            return v
        }

        override fun getItemViewType(position: Int): Int {
            return position % 2
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            if (getItemViewType(position) == 1) {

                val v = Button(parent?.context)
                v.text = "a Button View"

                v.setOnClickListener({
                    if (myCount == count1) {
                        myCount = count2
                    } else {
                        myCount = count1
                    }

                    notifyDataSetChanged()
                })

                return v
            } else {
                val v = TextView(parent?.context)
                v.text = "a Text View"
                return v
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}