/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.vwassa.example.utilities

import com.vwassa.example.list.LinkedList

class StringUtils {
    companion object {
        fun join(source: LinkedList): String {
            return JoinUtils.join(source)
        }

        fun split(source: String): LinkedList {
            return SplitUtils.split(source)
        }
    }
}
