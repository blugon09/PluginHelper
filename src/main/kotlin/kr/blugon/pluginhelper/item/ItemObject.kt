package kr.blugon.pluginhelper.item

import com.destroystokyo.paper.Namespaced
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class ItemObject(var type: Material = Material.AIR) {
//    var type = Material.AIR
    var amount = 1
    var displayName : TextComponent? = null
    var lore = arrayListOf<TextComponent>()
    val enchantment = HashMap<Enchantment, Int>()
    var customModelData = 0
    val itemFlag = arrayListOf<ItemFlag>()
    var unbreakable = false
    var attribute = HashMap<Attribute, ArrayList<AttributeModifier>>()
    var canPlace = arrayListOf<Material>()
    var canDestroy = arrayListOf<Material>()
    var damage = 0
    val maxDuration = this.type.maxDurability
    val rarity = this.type.itemRarity
//    val customDataTag = HashMap<NamespacedKey, HashMap<Any, Any>>()
//    val customDataTag = ArrayList<CustomDataTag>()



    //===================<Create>===================
    constructor(type: Material = Material.AIR, amount: Int = 1) : this() {
        this.type = type
        this.amount = amount
    }
    constructor(type: Material = Material.AIR, amount: Int = 1, displayName: TextComponent? = null) : this() {
        this.type = type
        this.amount = amount
        this.displayName = displayName
    }
    constructor(type: Material = Material.AIR, amount: Int = 1, displayName: TextComponent? = null, lore: ArrayList<TextComponent> = arrayListOf()) : this() {
        this.type = type
        this.amount = amount
        this.displayName = displayName
        this.lore = lore
    }



    //===================<Lore>===================
    fun getLineInLore(line : Int): TextComponent {
        return this.lore[line]
    }

    fun addLore(lore : TextComponent): ItemObject {
        this.lore.add(lore)
        return this
    }

    //===================<Enchantment>===================
    fun setEnchantment(enchantment : Enchantment, value : Int): ItemObject {
        this.enchantment[enchantment] = value
        return this
    }

    fun removeEnchantment(enchantment : Enchantment): ItemObject {
        this.enchantment.remove(enchantment)
        return this
    }

    //===================<ItemFlag>===================

    fun addItemFlag(itemFlag : ItemFlag): ItemObject {
        this.itemFlag.add(itemFlag)
        return this
    }

    fun removeItemFlag(itemFlag : ItemFlag): ItemObject {
        this.itemFlag.remove(itemFlag)
        return this
    }

    //===================<Attribute>===================
    fun addAttribute(attribute : Attribute, modifier : AttributeModifier): ItemObject {
        if(this.attribute[attribute] != null) {
            this.attribute[attribute]!!.add(modifier)
        } else {
            this.attribute[attribute] = arrayListOf(modifier)
        }
        return this
    }


    fun removeAttribute(attribute : Attribute): ItemObject {
        if(this.attribute.containsKey(attribute)) this.attribute.remove(attribute)
        return this
    }

    //===================<CanPlace>===================
    fun addCanPlace(canPlace : Material): ItemObject {
        if(this.canPlace.contains(canPlace)) return this
        this.canPlace.add(canPlace)
        return this
    }

    fun removeCanPlace(canPlace : Material): ItemObject {
        if(!this.canPlace.contains(canPlace)) return this
        this.canPlace.remove(canPlace)
        return this
    }

    //===================<CanDestroy>===================
    fun addCanDestroy(canDestroy : Material): ItemObject {
        if(this.canDestroy.contains(canDestroy)) return this
        this.canDestroy.add(canDestroy)
        return this
    }

    fun removeCanDestroy(canDestroy : Material): ItemObject {
        if(!this.canDestroy.contains(canDestroy)) return this
        this.canDestroy.remove(canDestroy)
        return this
    }

    //===================<CustomDataTag>===================
//    fun addCustomTag(plugin : JavaPlugin, key : String, value : Any): ItemObject {
//        customDataTag.add(CustomDataTag(NamespacedKey(plugin, key), value))
//        return this
//    }
//    fun containsCustomTag(key : String) : Boolean {
//        for(t in customDataTag) {
//            if(t.nameSpacedKey.key != key) continue
//            return t.nameSpacedKey.key == key
//        }
//        return false
//    }
//    fun getCustomTag(key : String) : Any {
//
//    }


    //===================<Clone>===================
    fun clone(): ItemObject {
        val array = ArrayList<ItemObject>()
        array.add(this)
        return array[0]
    }



    //===================<Build>===================
    fun build(): ItemStack {
        val stack = ItemStack(type, amount)
        val meta = stack.itemMeta

        //DisplayName
        if (displayName != null) {
            meta.displayName(displayName)
        }

        //Lore
        if (lore.isNotEmpty()) {
            meta.lore(lore as List<Component>)
        }

        //Enchantment
        if(enchantment.isNotEmpty()) {
            for (e in enchantment) {
                meta.addEnchant(e.key, e.value, true)
            }
        }

        //CustomModelData
        if(customModelData != 0) {
            meta.setCustomModelData(customModelData)
        }

        //ItemFlag
        if(itemFlag.isNotEmpty()) {
            for(f in itemFlag) {
                meta.addItemFlags(f)
            }
        }

        //Unbreakable
        meta.isUnbreakable = unbreakable

        //Attribute
        if(attribute.isNotEmpty()) {
            for(a in attribute) {
                for(m in a.value) {
                    meta.addAttributeModifier(a.key, m)
                }
            }
        }

        //CanPlace
        val place = mutableSetOf<Namespaced>()
        for (p in canPlace) {
            place.add(p.key)
        }
        meta.setPlaceableKeys(place)

        //CanDestroy
        val destroy = mutableSetOf<Namespaced>()
        for (d in canDestroy) {
            destroy.add(d.key)
        }
        meta.setDestroyableKeys(destroy)

        //Damage
        stack.durability = damage.toShort()


        stack.itemMeta = meta
        return stack
    }
}



@Deprecated("아이템의 nbt데이터가 적용되지 않는 버그 확인, 현재 고치기 불가능")
fun ItemStack.asItemObject(): ItemObject {
    val lore = this.lore
    val nLore = arrayListOf<String>()
    if(lore != null) {
        for (l in lore) {
            nLore.add(l)
        }
    }


    //Type, Amount, DisplayName, Lore
    val itemObject = ItemObject(this.type, this.amount)

    //DisplayName
    if(this.itemMeta.displayName() != null) {
        itemObject.displayName = this.itemMeta.displayName() as TextComponent
    }

    //Lore
    if(this.itemMeta.lore() != null) {
        if(this.itemMeta.lore()!!.isNotEmpty()) {
            for(l in this.itemMeta.lore()!!) {
                itemObject.addLore(l as TextComponent)
            }
        }
    }

    //Unbreakable
    itemObject.unbreakable = this.itemMeta.isUnbreakable

    //CustomModelData
    if(this.itemMeta.hasCustomModelData()) {
        itemObject.customModelData = this.itemMeta.customModelData
    }

    //Enchantments
    for(e in this.enchantments) {
        itemObject.setEnchantment(e.key, e.value)
    }

    //ItemFlag
    for(f in this.itemFlags) {
        itemObject.addItemFlag(f)
    }

    //Attribute
    if(this.itemMeta.attributeModifiers != null) {
        if(!this.itemMeta.attributeModifiers!!.isEmpty) {
            val mulimap = this.itemMeta.attributeModifiers!!
            val attributes = this.itemMeta.attributeModifiers!!.asMap()!!

            for(a in attributes) {
                for(m in mulimap[a.key]) {
                    itemObject.addAttribute(a.key, m)
                }
            }
        }
    }


    //CanPlace
    for(p in this.itemMeta.canPlaceOn) {
        itemObject.addCanPlace(p)
    }

    //CanDestroy
    for(d in this.itemMeta.canDestroy) {
        itemObject.addCanDestroy(d)
    }

    //Damage
    itemObject.damage = this.durability.toInt()

    return itemObject
}