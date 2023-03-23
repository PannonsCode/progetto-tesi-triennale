//provatesiinitalpose
#include <ros/ros.h>
#include <time.h>
#include <iostream>
#include "provaTesi.h"

geometry_msgs::PoseWithCovarianceStamped initPose;
ros::Publisher initP;

void initial_pose(const nav_msgs::Odometry &msg){
	
	std::cout<<"frame_id :"<<msg.header.frame_id<<"\n";
	std::cout<<msg.pose.pose.position<<"\n";
	
	initPose.header=msg.header;
	initPose.pose.pose.position=msg.pose.pose.position;
	initPose.pose.pose.orientation=msg.pose.pose.orientation;
	
	initP.publish(initPose);
}

//int main(int argc, char** argv){

void set_initial_pose(){

	//ros::init("initial_pose_node");
	
	ros::NodeHandle n;
	initP = n.advertise<geometry_msgs::PoseWithCovarianceStamped>("/initialpose",1);
	
	/*Publish initialPose
	ros::Publisher init=n.advertise<geometry_msgs::PoseWithCovarianceStamped>("/initialpose",1);
	geometry_msgs::PoseWithCovarianceStamped initPose;
	initPose.header.frame_id = "odom";
	initPose.header.stamp=ros::Time::now();
	initPose.pose.pose.position.x=-11.23;
	initPose.pose.pose.position.y=23.26;
	initPose.pose.pose.position.z=0.0;
	initPose.pose.pose.orientation.w=180.0;
	init.publish(initPose);*/
	
	ros::Subscriber sub = n.subscribe("/base_pose_ground_truth",1,initial_pose);
	
    //ros::spin();

	//return 0;
}

