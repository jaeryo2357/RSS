package com.mut_jaeryo.rss.util

import org.w3c.dom.Element


fun Element.findValue(name : String) : String {
        return this.getElementsByTagName(name).item(0).firstChild.nodeValue
}
