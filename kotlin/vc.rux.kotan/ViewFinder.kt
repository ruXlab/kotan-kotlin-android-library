package vc.rux.kotan

import android.app.Activity
import android.app.Fragment
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Helper class, implements findView for different types of UI object
 */
abstract class FindViewBase<out T : View>(private val viewId: Int) : ReadOnlyProperty<Any, T> {
    protected fun findById(thisRef: Any): T =
            when (thisRef) {
                is Activity -> thisRef.findViewById(viewId) as T
                is Fragment -> thisRef.view.findViewById(viewId) as T
                is View -> thisRef.findViewById(viewId) as T
                else -> throw IllegalArgumentException("Delegate can't be attached to this object")
            }

}


/**
 * Lazy implementation of findView. Will be evaluated on first access
 */
class LazyView<T : View>(private val viewId: Int) : FindViewBase<T>(viewId) {
    private var value: T? = null

    override fun getValue(thisRef: Any, desc: KProperty<*>): T {
        if (value == null) value = findById(thisRef)
        return value!!
    }
}

/**
 * Lazy implementation of findView. Will be evaluated on first access
 */
class LazyViewCustomParent<out T : View>(val viewId: Int, val parentView: View) : ReadOnlyProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, desc: KProperty<*>): T {
        if (value == null) value = parentView.findViewById<T>(viewId)
        return value!!
    }
}


/**
 * Find UI component
 */
//class FindView<T>(private val viewId: Int) : FindViewBase<T>(viewId) {
//    private val value: T = findById(viewId)
//
//    override fun get(thisRef: Any, desc: PropertyMetadata): T {
//        return value
//    }
//}