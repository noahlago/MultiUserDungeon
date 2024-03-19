package model.Tiles;

import model.Item;
import model.Visitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class MerchantTile extends ConcreteTile{
    @JsonProperty("goods") private Item[] goods;

    @JsonCreator
    public MerchantTile(){
        this.goods = new Item[3];
        //TODO add random items to merchants goods
    }

    public void accept(Visitor visitor){
        //Double dispatch, object calls visitors proper method
        visitor.visitMerchantTile(this);
    }

    public Item[] getGoods(){
        return goods;
    }

    public void setGoods(Item[] items){
        this.goods = items;
    }

    @Override
    public String toString(){
        return "[ M ]";
    }
    
    
}
