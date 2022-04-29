package kr.blugon.pluginhelper.inventory

import kr.blugon.pluginhelper.component.component
import kr.blugon.pluginhelper.item.ItemObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class InventoryObject(val size: Int) {
   var title : Component = "Inventory".component()
   val items = Array(size) {
      ItemStack(Material.AIR)
   }
//   var isEmpty = true


   //===================<Create>===================
   constructor(size: Int, title: Component) : this(size) {
      this.title = title
   }


   //===================<Functions>===================

   fun clear(): InventoryObject {
      for(i in 0 until size) {
         this.items[i] = ItemStack(Material.AIR)
      }
//      isEmpty = true
      return this
   }

   fun isAir(slot : Int) : Boolean {
      if(items[slot+1].type == Material.AIR) return true
      return false
   }

   fun build(): Inventory {
      val inventory = Bukkit.createInventory(null, size, title)
      for(i in 0 until inventory.size) {
         inventory.setItem(i, items[i])
      }
      return inventory
   }
}

fun InventoryObject.subtract(type : Material) : Boolean {
   for(i in 0 until this.size) {
      if(this.items[i].type == Material.AIR) continue
      val subtractitem = this.items[i]

      if(subtractitem.type != type) continue
      if(1 <= subtractitem.amount) {
         this.items[i].amount = this.items[i].amount-1
         return true
      }
   }
   return false
}


fun InventoryObject.subtract(type : Material, amount : Int) : Boolean {
   val inventory = this
   var total = 0

   for(i in 0 until inventory.size) {
      if(this.items[i].type == Material.AIR) continue
      val subtract = inventory.items[i]

      if(subtract.type != type) continue
      if(amount <= subtract.amount) {
         inventory.items[i].amount = inventory.items[i].amount-amount
         return true
      } else {
         total += subtract.amount
      }
   }
   if(amount <= total) {
      total = amount
      for(i in 0 until inventory.size) {
         if(inventory.items[i].type == Material.AIR) continue
         val subtract = inventory.items[i]

         if(subtract.type != type) continue
         for(i2 in 0..subtract.amount) {
            if(0 < total) {
               if(inventory.items[i].type == Material.AIR) continue
               total--
               inventory.items[i].amount = inventory.items[i].amount - 1
            } else {
               return true
            }
         }
      }
   }
   return false
}