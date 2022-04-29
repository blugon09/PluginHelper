package kr.blugon.pluginhelper.inventory

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

fun Inventory.subtract(type : Material) : Boolean {
    for(i in 0 until this.size) {
        if(this.getItem(i) == null) continue
        val subtractitem = this.getItem(i)!!

        if(subtractitem.type != type) continue
        if(1 <= subtractitem.amount) {
            this.getItem(i)!!.amount = this.getItem(i)!!.amount-1
            return true
        }
    }
    return false
}


fun Inventory.subtract(type : Material, amount : Int) : Boolean {
    val inventory = this
    var total = 0

    for(i in 0 until inventory.size) {
        if(inventory.getItem(i) == null) continue
        val subtract = inventory.getItem(i)!!

        if(subtract.type != type) continue
        if(amount <= subtract.amount) {
            inventory.getItem(i)!!.amount = inventory.getItem(i)!!.amount-amount
            return true
        } else {
            total += subtract.amount
        }
    }
    if(amount <= total) {
        total = amount
        for(i in 0 until inventory.size) {
            if(inventory.getItem(i) == null) continue
            val subtract = inventory.getItem(i)!!

            if(subtract.type != type) continue
            for(i2 in 0..subtract.amount) {
                if(0 < total) {
                    if(inventory.getItem(i) != null) {
                        total--
                        inventory.getItem(i)!!.amount = inventory.getItem(i)!!.amount - 1
                    } else {
                        continue
                    }
                } else {
                    return true
                }
            }
        }
    }
    return false
}


@Deprecated("Inventory.contains(Material)")
fun Inventory.exist(type : Material) : Boolean {
    for(i in 0 until this.size) {
        if(this.getItem(i) == null) continue
        val item = this.getItem(i)!!

        if (item.type != type) continue
        return true
    }
    return false
}

@Deprecated("Inventory.contains(Material, Amount)")
fun Inventory.exist(type : Material, amount : Int) : Boolean {
    var total = 0

    for(i in 0 until this.size) {
        if(this.getItem(i) == null) continue
        val item = this.getItem(i)!!

        if(item.type != type) continue
        if(amount <= item.amount) return true
        else total += item.amount
    }
    if(amount <= total) {
        return true
    }
    return false
}


//Player
fun Player.subtract(type : Material) : Boolean {
    return this.inventory.subtract(type)
}


fun Player.subtract(type : Material, amount : Int) : Boolean {
    return this.inventory.subtract(type, amount)
}


fun Player.contains(type : Material) : Boolean {
    return this.inventory.contains(type)
}


fun Player.contains(type : Material, amount : Int) : Boolean {
    return this.inventory.contains(type, amount)
}