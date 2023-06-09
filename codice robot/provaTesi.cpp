#include <ros/ros.h>
#include <move_base_msgs/MoveBaseAction.h>
#include <actionlib/client/simple_action_client.h>
#include <geometry_msgs/PoseWithCovarianceStamped.h>
#include <time.h>
#include <iostream>

typedef actionlib::SimpleActionClient<move_base_msgs::MoveBaseAction> MoveBaseClient;

int main(int argc, char** argv){

	ros::init(argc, argv, "simple_navigation_goals");
	
	//take parametrs
	if(argc!=3){
		ROS_INFO("Insert next goal coordinates x and y");
		return 0;
	}
	int x = atoi(argv[1]);
	int y = atoi(argv[2]);
	std::cout<<"Hai inserito x: "<<x<<" e y: "<<y<<"\n";

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
		
	return 0;
}
