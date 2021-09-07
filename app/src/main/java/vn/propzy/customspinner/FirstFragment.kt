package vn.propzy.customspinner

import android.content.Context
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
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.spinner.adapter =
            CustomSpinnerAdapter(requireContext(), CreateFakeData.dataSpinner())

    }

    class CustomSpinnerAdapter(var context: Context, var data: MutableList<TypeGroup>) :
        SpinnerGroupAdapter() {

        override fun getChildItemViewType(group: Int, child: Int): Int {
            return 2
        }

        override fun getGroupItemViewType(group: Int): Int {
            return 1
        }

        override fun getGroupItem(group: Int): Any {
            return data[group]
        }

        override fun getChildItem(group: Int, child: Int): Any {
            return data[group]?.children?.get(child) ?: TypeItem()
        }

        override fun getGroupCount(): Int {
            return data.size
        }

        override fun getItemCountInGroup(group: Int): Int {

            if (data[group].isExpended) {
                return data[group].children?.size ?: 0
            }

            return 0
        }

        override fun getChildItemView(
            group: Int,
            child: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            //should change it to another layout, we only use this temporarily for demo
            //it should not use from getDropDownChildItemView
            return createChildView(convertView, parent, data[group].children?.get(child))
        }

        override fun getGroupItemView(group: Int, convertView: View?, parent: ViewGroup?): View {
            //should change it to another layout, we only use this temporarily for demo
            //it should not use from getDropDownGroupItemView
            return createParentView(convertView, parent, data[group])
        }

        override fun getDropDownChildItemView(
            group: Int,
            child: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            return createChildView(convertView, parent, data[group].children?.get(child))
        }

        override fun getDropDownGroupItemView(
            group: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            return createParentView(convertView, parent, data[group])
        }

        private fun createChildView(
            convertView: View?,
            parent: ViewGroup?,
            item: TypeItem?,
        ): View {
            val view = LayoutInflater.from(context).inflate(
                R.layout.item_spinner_property_type_child,
                parent,
                false
            )
            view.findViewById<TextView>(R.id.tvTitle).text = item?.name
            return view
        }

        private fun createParentView(
            convertView: View?,
            parent: ViewGroup?,
            item: TypeGroup,
        ): View {
            val view = LayoutInflater.from(context).inflate(
                R.layout.item_spinner_property_type_parent,
                parent,
                false
            )
            view.findViewById<TextView>(R.id.tvTitle).text = item.name

            view.setOnClickListener {

                val selectedItem = data.firstOrNull { it.isExpended }

                selectedItem?.isExpended = false

                item.isExpended = true

                notifyDataSetChanged()
            };
            return view
        }

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
                v.text = "a Button View $position"

                v.setOnClickListener {
                    myCount = if (myCount == count1) {
                        count2
                    } else {
                        count1
                    }

                    notifyDataSetChanged()
                }

                return v
            } else {
                val v = TextView(parent?.context)
                v.text = "a Text View $position"
                return v
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
