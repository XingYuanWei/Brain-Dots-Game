package com.example.astronaut.brain_dots.Domain;

/*
 *Created by 魏兴源 on 2018-07-08
 *Time at 22:03
 *
 */


import android.support.annotation.NonNull;


import com.example.astronaut.brain_dots.Shapes.common.Ball;
import com.example.astronaut.brain_dots.Shapes.common.Line;
import com.example.astronaut.brain_dots.Shapes.common.Rectangle;

import com.example.astronaut.brain_dots.Utils.Constant;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


/**
 * 此类为造物主类，用于创建各种各样的刚体或图形
 * 长方体 三角形 不规则图形 圆形等
 * 都被封装为静态方法用于直接创建刚体
 * 根据前期的需求，先能够创建矩形，圆形，直线，三角形，曲线等
 */
public class Creator {
    /**
     * @CreateRectangle(float,float,float,float,boolean,World,int) 方法描述:创建一个矩形 矩形的开始画的地方在它的中点
     * @ float x
     *      以左上角为原点时的x坐标
     * @ float y
     *      以左上角为原点时的y坐标
     * @ float halfWidth
     *      矩形的半宽(为什么是半宽的原因已解释，详情看Rectangle类)
     * @ float halfHeight
     *      矩形的半长(为什么是半长的原因已解释，详情看Rectangle类)
     * @ boolean isStatic
     *      此参数用于表示这个刚体在"世界"中是为静止的，还是为运动的
     *      何谓静止何谓运动。所谓静止就是如果在设置了重力加速度的情况下，
     *      此时如果它的值为true，则表示它不受到重力作用，就一直静止在"世界"中。
     *      如果它的值为false，则相反，表示它受到重力的作用
     * @ World world
     *      该参数表示"世界"。何谓"世界"? 所谓"世界"就是在 JBox2D 中表示的是物理世界。
     *      一个物理世界就是物体、形状和约束相互作用的集合，
     *      开发人员可以在该物理世界中创建或者删除所需的刚体或关节以实现所需的物理模拟。
     *      要注意的是，创建一个物理世界对象，必须要给出其重力向量（若没有重力可以给 0 值）
     * @ int color
     *      颜色
     */
    @NonNull
    public static Rectangle createRectangle(float x, float y,
                                            float halfWidth, float halfHeight,
                                            boolean isStatic, World world, int color) {
        /**
         *
         * BodyDef该类的实例主要用于存储一些刚体的属性信息，
         * 在创建刚体时一般要通过该类的实例给出刚体的相关属性信息。
         * 这些属性信息主要包括刚体位置的坐标值、线速度值、
         * 角速度值、线性阻尼值以及角度阻 尼值等
         *
         * */
        BodyDef bodyDef = new BodyDef();
        //判断刚体为静止或运动状态
        bodyDef.type = isStatic ? BodyType.STATIC : BodyType.DYNAMIC;

        //设置位置
        bodyDef.position.set(x / Constant.RATE, y / Constant.RATE);
        //在"世界"中创建刚体
        Body rigidBody = world.createBody(bodyDef);
        //刚体的形状
        PolygonShape polygon = new PolygonShape();
        //设定边框
        polygon.setAsBox(halfWidth / Constant.RATE, halfHeight / Constant.RATE);
        /**
         *
         * FixtureDef刚体的物理描述
         * 其中主要是包含一些记录不同物理属性的成员，
         * 如摩擦系数，恢复系数以及密度等
         *
         * */
        FixtureDef fixtureDef = new FixtureDef();
        /**
         * @density：密度
         * */
        fixtureDef.density = 1.0f;
        /**
         * @friction：摩擦系数
         * */
        fixtureDef.friction = 0.05f;
        /**
         *@restitution：恢复系数或称为硬度
         * */
        fixtureDef.restitution = 0.2f;
        /**
         * @shape：形状
         * */
        fixtureDef.shape = polygon;
        //将物理描述与刚体结合
        if (!isStatic) {
            rigidBody.createFixture(fixtureDef);
        } else {
            rigidBody.createFixture(polygon, 0);
        }
        return new Rectangle(rigidBody, color, halfWidth, halfHeight);
    }


    /**
     * 创建一个带有固定倾角的矩形
     */
    public static Rectangle createRectangle(float x, float y,
                                            float halfWidth, float halfHeight,
                                            boolean isStatic, World world, int color, float angle) {
        BodyDef bodyDef = new BodyDef();
        //判断刚体为静止或运动状态
        bodyDef.type = isStatic ? BodyType.STATIC : BodyType.DYNAMIC;
        //设置位置
        bodyDef.position.set(x / Constant.RATE, y / Constant.RATE);
        //在"世界"中创建刚体
        Body rigidBody = world.createBody(bodyDef);
        //刚体的形状
        PolygonShape polygon = new PolygonShape();
        //设定边框
        polygon.setAsBox(halfWidth / Constant.RATE, halfHeight / Constant.RATE);
        FixtureDef fixtureDef = new FixtureDef();
        //密度
        fixtureDef.density = 2f;
        //摩擦系数
        fixtureDef.friction = 0.2f;
        //恢复系数
        fixtureDef.restitution = 0f;
        //形状
        fixtureDef.shape = polygon;
        //将物理描述与刚体结合
        if (!isStatic) {
            rigidBody.createFixture(fixtureDef);
        } else {
            rigidBody.createFixture(polygon, 0);
        }
        return new Rectangle(rigidBody, color, halfWidth, halfHeight, angle);
    }

    @NonNull
    public static Ball createBall(float x, float y, float radius, boolean isStatic, World world, int color) {
        BodyDef bodyDef = new BodyDef();
        //球一定是运动的
        bodyDef.type = isStatic ? BodyType.STATIC : BodyType.DYNAMIC;
        bodyDef.position.set(x / Constant.RATE, y / Constant.RATE);
        //在"世界"中创建刚体
        Body rigidBody = world.createBody(bodyDef);
        //创建刚体形状
        CircleShape circle = new CircleShape();
        //获取物理世界圆的半径
        circle.m_radius = radius / Constant.RATE;
        //刚体的物理性质描述
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.07f;
        fixtureDef.shape = circle;
        rigidBody.createFixture(fixtureDef);
        return new Ball(rigidBody, radius, color);
    }

    /**
     * 创建线段
     */
    public static Line createLine(float startX, float startY, float endX,
                                  float endY, boolean isStatic, World world, int color) {
        //静态或动态
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyType.STATIC : BodyType.DYNAMIC;

        float centerX = (startX + endX) / 2f;
        float centerY = (endX + endY) / 2f;
        //位置
        bodyDef.position.set(centerX / Constant.RATE, centerY / Constant.RATE);
        //线段长
        float lineLength = getDistanceTwoPoint(startX, startY, endX, endY);
        //世界中创建刚体
        Body rigidBody = world.createBody(bodyDef);
        //刚体物理性质
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 4.f;
        fixtureDef.restitution = 0.9f;
        fixtureDef.friction = 0.5f;
        //形状
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vec2(centerX / Constant.RATE, centerY / Constant.RATE),
                new Vec2(centerX / Constant.RATE, centerY / Constant.RATE));
        fixtureDef.shape = edgeShape;
        rigidBody.createFixture(fixtureDef);
        return new Line(rigidBody, color, lineLength);
    }


    /**
     * 两个点之间的距离
     * 四个个参数
     *
     * @param startX 起始X坐标
     * @param startY 起始Y坐标
     * @param endX   结束点X坐标
     * @param endY   结束点Y坐标
     */
    private static float getDistanceTwoPoint(float startX, float startY, float endX, float endY) {
        return (float) Math.sqrt((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY));
    }

    /**
     * 根据集合中的内容，创建线段的组合
     */


}
