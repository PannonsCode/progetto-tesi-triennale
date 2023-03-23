//Nodo socket
#include <ros/ros.h>
#include <move_base_msgs/MoveBaseAction.h>
#include <actionlib/client/simple_action_client.h>
#include <geometry_msgs/PoseWithCovarianceStamped.h>
#include <time.h>
#include <iostream>

#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stdio.h>

#include "provaTesi.h"

#define SERVER_PORT 3000

typedef actionlib::SimpleActionClient<move_base_msgs::MoveBaseAction> MoveBaseClient;

int main(int argc, char** argv){

	ros::init(argc, argv, "simple_navigation_goals_with_socket");
	
	//set initial pose
	set_initial_pose();
	
	
	//init socket
	int sock, lenght, n;
	socklen_t len;
	struct sockaddr_in server, client;
	char buf[4];
	
	sock = socket(AF_INET, SOCK_DGRAM, 0);
	if(sock<0){
		perror("Opening socket");
		exit(0);
	}
	
	lenght = sizeof(server);
	bzero(&server,lenght);
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_family = AF_INET;
	server.sin_port = htons(SERVER_PORT);
	
	if(bind(sock,(struct sockaddr*)&server,lenght)<0){
		perror("binding");
		exit(0);
	}
	len=sizeof(struct sockaddr_in);
	
	//ricezione ed elaborazione messaggio
	n=recvfrom(sock,buf,4,0,(struct sockaddr*)&client,&len);
	if(n<0) {perror("Errore ricezione"); exit(0);}
	int x = atoi(buf);
	
	bzero(buf,4);
	n=recvfrom(sock,buf,4,0,(struct sockaddr*)&client,&len);
	if(n<0) {perror("Errore ricezione"); exit(0);}
	int y = atoi(buf);
	
	//flag 
	bzero(buf,4);
	n=recvfrom(sock,buf,4,0,(struct sockaddr*)&client,&len);
	if(n<0) {perror("Errore ricezione"); exit(0);}
	int flag = atoi(buf);
	
	std::cout<<"Ricevuto: x = "<<x<<" e y = "<<y<<"\n";

	//tell the action client that we want to spin a thread by default
	MoveBaseClient ac("move_base", true);

	//wait for the action server to come up
	while(!ac.waitForServer(ros::Duration(5.0))){
		ROS_INFO("Waiting for the move_base action server to come up");
	}

	move_base_msgs::MoveBaseGoal goal;

	//first goal
	goal.target_pose.header.frame_id = "base_link" ;
	goal.target_pose.header.stamp = ros::Time::now();
	goal.target_pose.pose.position.x = x;
	goal.target_pose.pose.position.y = y;
	goal.target_pose.pose.position.z = 0.0;
	goal.target_pose.pose.orientation.w = 1.0;
	ROS_INFO ("Sending goal" );
	ac.sendGoal(goal);
	
	//wait result
	ac.waitForResult();
	
	if(ac.getState() == actionlib::SimpleClientGoalState::SUCCEEDED || 
	   ac.getState() == actionlib::SimpleClientGoalState::ABORTED)
		ROS_INFO("Goal completed" );
	else{
		ROS_INFO("The base failed to move for some reason");
		//cancel goal
		ac.cancelAllGoals();
	}
	
	//set initial pose
	set_initial_pose();
	
	//check flag
	if(flag==0){
		x=-11;
		y=23;
	}
	else{
		n=recvfrom(sock,buf,4,0,(struct sockaddr*)&client,&len);
		if(n<0) {perror("Errore ricezione"); exit(0);}
		int x = atoi(buf);
	
		bzero(buf,4);
		n=recvfrom(sock,buf,4,0,(struct sockaddr*)&client,&len);
		if(n<0) {perror("Errore ricezione"); exit(0);}
		int y = atoi(buf);
	}
	
	//second goal
	goal.target_pose.header.frame_id = "base_link" ;
	goal.target_pose.header.stamp = ros::Time::now();
	goal.target_pose.pose.position.x = x;
	goal.target_pose.pose.position.y = y;
	goal.target_pose.pose.position.z = 0.0;
	goal.target_pose.pose.orientation.w = 1.0;
	ROS_INFO ("Sending goal" );
	ac.sendGoal(goal);
	
	//wait result
	ac.waitForResult();
	
	if(ac.getState() == actionlib::SimpleClientGoalState::SUCCEEDED || 
	   ac.getState() == actionlib::SimpleClientGoalState::ABORTED)
		ROS_INFO("Goal completed" );
	else{
		ROS_INFO("The base failed to move for some reason");
		//cancel goal
		ac.cancelAllGoals();
	}
	
	ros::spin();
		
	return 0;
}
