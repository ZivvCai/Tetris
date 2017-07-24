package com.example.tetris.model;

import com.example.tetris.view.TetrisView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/20.
 */

public class TetrisBlock implements Cloneable {
    /*
     *俄罗斯方块
     */
    public final static int TYPE_SUM = 7;//方块总的种类数
    public final static int DIRECTION_SUM = 4;//方块总的方向数
    private int blockType;//方块种类
    private int blockDirection;//方块方向
    private int color;//方块颜色
    private float x,y;//方块坐标
    private ArrayList<BlockUnit> units = new ArrayList<>();//方块组成部分
    private ArrayList<TetrisBlock> blocks = new ArrayList<>();//所有俄罗斯方块

    public TetrisBlock(float x,float y){
        /*
         * 构造函数
         */
        this.x = x;
        this.y = y;

        blockType = (int)(Math.random() * TYPE_SUM) + 1;   //随机生成一个种类
        blockDirection = (int)(Math.random()*DIRECTION_SUM)+1;      //默认初始方向
        color = (int)(Math.random() * 5) + 1;             //随机生成一个颜色

        switch(blockType){
            case 1:
                for(int i=0;i<4;i++){
                    units.add(new BlockUnit(x + (-2 + i ) * BlockUnit.UNITSIZE , y));
                }
                break;
            case 2:
                units.add(new BlockUnit(x + (-1 + 1 ) * BlockUnit.UNITSIZE , y - BlockUnit.UNITSIZE));
                for(int i=0;i<3;i++){
                    units.add(new BlockUnit(x + (-1 + i ) * BlockUnit.UNITSIZE , y ));
                }
                break;
            case 3:
                for(int i=0;i<2;i++){
                    units.add(new BlockUnit(x + (i-1) * BlockUnit.UNITSIZE,y - BlockUnit.UNITSIZE ));
                    units.add(new BlockUnit(x + (i-1) * BlockUnit.UNITSIZE,y  ));
                }
                break;
            case 4:
                units.add(new BlockUnit(x + (-1 + 0 ) * BlockUnit.UNITSIZE , y - BlockUnit.UNITSIZE));
                for(int i=0;i<3;i++){
                    units.add(new BlockUnit(x + (-1 + i ) * BlockUnit.UNITSIZE , y ));
                }
                break;
            case 5:
                units.add(new BlockUnit(x + (-1 + 2 ) * BlockUnit.UNITSIZE , y - BlockUnit.UNITSIZE));
                for(int i=0;i<3;i++){
                    units.add(new BlockUnit(x + (-1 + i ) * BlockUnit.UNITSIZE , y ));
                }
                break;
            case 6:
                for(int i=0;i<2;i++){
                    units.add(new BlockUnit(x + (-1+i) * BlockUnit.UNITSIZE,y - BlockUnit.UNITSIZE ));
                    units.add(new BlockUnit(x + i * BlockUnit.UNITSIZE,y ));
                }
                break;
            case 7:
                for(int i=0;i<2;i++){
                    units.add(new BlockUnit(x + i * BlockUnit.UNITSIZE,y - BlockUnit.UNITSIZE ));
                    units.add(new BlockUnit(x + ( -1 + i )* BlockUnit.UNITSIZE,y ));
                }
                break;
        }
    }

    public TetrisBlock(TetrisBlock other){
        this.x = other.x;
        this.y = other.y;
        this.color = other.color;
        this.blockDirection = other.blockDirection;
        this.blockType = other.blockType;
        this.blocks = other.blocks;
    }
    public void remove(int j){
        /*
         *删除在第j行上的方块单元
         *@param 删除行标
         */
        for(int i = units.size()-1;i >= 0;i--){
            /*
             *逆向遍历
             * 根据y坐标计算单元所在行，若为j行则从units中删除
             */
            if((int)((units.get(i).getY() - TetrisView.beginPoint)/50) == j){
                units.remove(i);
            }
        }
    }

    @Override
    public TetrisBlock clone() throws CloneNotSupportedException {
        TetrisBlock block = new TetrisBlock(this);
        for(BlockUnit unit : getUnits()){
            block.units.add(unit.clone());
        }
        return block;
    }

    public boolean canRotate(){
        /*
         * 判断方块是否能够翻转
         * @return 若能翻转返回true
         */
        for(TetrisBlock b:blocks){
            //遍历俄罗斯方块所有单元，是否均能翻转，若其中一个单元不能，则俄罗斯方块也不能翻转
            if(canRotate(b)==false){
                return false;
            }
        }
        return true;
    }

    public boolean canRotate(TetrisBlock other){
        /*
         * 判断方块是否能够翻转
         * @return 若能翻转返回true
         * @param 另一俄罗斯方块
         */
        for(BlockUnit i:units){
            //遍历俄罗斯方块所有单元，是否均能翻转，若其中一个单元不能，则俄罗斯方块也不能翻转
            for(BlockUnit j:other.getUnits() ){
                if(i.canRotate(j) == false){
                    return false;
                }
            }
        }
        return true;
    }

    public void move(int x){
        /*
         * 俄罗斯方块左右移动
         */

        // 检查是否接触边界，若接触边界，并且往接触边界移动会超界，故返回
        if((checkCollision_X() <0 && x<0)||(checkCollision_X()>0&&x>0))
            return;

        //更新移动后的坐标
        if(x > 0)
            setX(getX() + BlockUnit.UNITSIZE);
        else
            setX(getX() - BlockUnit.UNITSIZE);
    }

    public boolean checkCollision_Y(){
        /*
         * 判断俄罗斯方块是否与其他俄罗斯方块或者边界（底部）接触
         * @return 若接触返回true
         */
        for(BlockUnit u:units){
            //遍历所有单元块判断是否接触底部
            if(u.checkOutOfBoundary_Y())
                return true;
        }
        for(TetrisBlock block:blocks){
            //判断是否与其他俄罗斯方块接触
            if(this == block) {
                continue;
            }
            //判断俄罗斯方块底部是否接触其他俄罗斯方块
            if(checkCollision_Y(block))
                return true;
        }
        return false;
    }

    public int checkCollision_X(){
        /*
         * 判断俄罗斯方块是否与其他俄罗斯方块或者边界（两侧）接触
         * @return 若接触返回true
         */
        for(BlockUnit u:units){
            //遍历所有单元块判断是否接触两侧
            if(u.checkOutOfBoundary_X() != 0)
                return u.checkOutOfBoundary_X();
        }
        for(TetrisBlock block:blocks){
            if(this == block)
                continue;
            //判断俄罗斯方块两侧是否接触其他俄罗斯方块
            if(checkCollision_X(block) != 0)
                return checkCollision_X(block);
        }
        return 0;
    }

    public boolean checkCollision_Y(TetrisBlock other){
        /*
         * 判断俄罗斯方块是否与其他俄罗斯方块或者边界（底部）接触
         * @return 若接触返回true
         */
        for(BlockUnit i: units){
            //遍历所有单元块判断是否接触底部
            for(BlockUnit j:other.units){
                if(i == j) {
                    continue;
                }
                //判断俄罗斯方块底部是否接触其他俄罗斯方块
                if(i.checkVerticalCollision(j))
                    return true;
            }
        }
        return false;
    }

    public int checkCollision_X(TetrisBlock other){
        /*
         * 判断俄罗斯方块是否与其他俄罗斯方块或者边界（两侧）接触
         * @return 若接触返回true
         */
        for(BlockUnit i: units){
            //遍历所有单元块判断是否接触两侧
            for(BlockUnit j:other.units){
                if(i == j)
                    continue;

                //判断俄罗斯方块两侧是否接触其他俄罗斯方块
                if(i.checkHorizontalCollision(j)!=0)
                    return i.checkHorizontalCollision(j);
            }
        }
        return 0;
    }


    public ArrayList<BlockUnit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<BlockUnit> units) {
        this.units = units;
    }

    public ArrayList<TetrisBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<TetrisBlock> blocks) {
        this.blocks = blocks;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        /*
         * 设置俄罗斯方块坐标
         * @param 新坐标值
         */
        float dif_x = x - this.x; //x增量

        for (BlockUnit u:units){
            //根据增量更新方块单元坐标
            u.setX(u.getX() + dif_x);
        }
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        /*
         * 设置俄罗斯方块坐标
         * @param 新坐标值
         */

        //若纵坐标超界则返回
        if(checkCollision_Y())
            return;
        float dif_y = y - this.y;//y增量
        for (BlockUnit u:units){
            //根据增量更新方块单元坐标
            u.setY(u.getY() + dif_y);
        }
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void rotate(){
        if(checkCollision_X()!=0 && checkCollision_Y() || blockType == 3)
            return;

        //按顺时针旋转
        for(BlockUnit u:units){
            float tx = u.getX();
            float ty = u.getY();
            u.setX(-(ty - y) + x);
            u.setY( tx - x + y) ;
        }
    }
}
