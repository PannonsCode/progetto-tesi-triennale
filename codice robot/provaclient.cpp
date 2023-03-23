
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <netdb.h>

#define SERVER_PORT 3000

using namespace std;

int main(int argc, char** argv){
	
	//init socket
	int sock, lenght, n;
	socklen_t len;
	struct sockaddr_in server, client;
	struct hostent *hp;
	char buf[1024];
	
	sock = socket(AF_INET, SOCK_DGRAM, 0);
	if(sock<0){
		perror("Opening socket");
		exit(0);
	}
	
	hp=gethostbyname("192.168.1.147");
	bcopy((char*)hp->h_addr,(char*)&server.sin_addr,hp->h_length);
	server.sin_family = AF_INET;
	server.sin_port = htons(SERVER_PORT);
	
	lenght = sizeof(sockaddr_in);
	
    bzero(buf,1024);
	fgets(buf,1024,stdin);
	n=sendto(sock,buf,strlen(buf),0,(const struct sockaddr*)&server,lenght);
	//cout<<"Inviato: "<<buf<<"\n";
		
	return 0;
}
