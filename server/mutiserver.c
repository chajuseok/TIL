// server_01.c

#include <stdio.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>


// in your browser type: http://localhost:8090
// IF error: address in use then change the PORT number
#define PORT 8090


int main(int argc, char const *argv[])
{
    int server_fd, new_socket; long valread;
    struct sockaddr_in address;
    int addrlen = sizeof(address);

    char *hello = "HTTP/1.1 200 OK\nContent-Type: text/plain" \
                  "Content-Length: 20\n\nMy first web server!";

    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0)
    {
        perror("In socket");
        exit(EXIT_FAILURE);
    }


    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons( PORT );

    memset(address.sin_zero, '\0', sizeof address.sin_zero);


    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address))<0)
    {
        perror("In bind");
        exit(EXIT_FAILURE);
    }
    if (listen(server_fd, 10) < 0)
    {
        perror("In listen");
        exit(EXIT_FAILURE);
    }
    while(1)
    {
        printf("\n+++++++ Waiting for new connection ++++++++\n\n");
        if ((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen))<0)
        {
            perror("In accept");
            exit(EXIT_FAILURE);
        }

        printf("socket %d\n",new_socket);
        pid_t pid;
        pid = fork();   // 포크

        if (pid > 0) {  // 부모 프로세스 부분
            // 부모 프로세스 코드 실행
            close(new_socket);  // 연결 종료
            printf("-------------close---------------\n");
        }
        else if (pid == 0) {    // 자식 프로세스 부분
            // 자식 프로세스 코드 실행
            char buffer[30000] = {0};
            valread = read( new_socket , buffer, 30000);
            printf("%s\n",buffer );
                // uncomment following line and connect many clients
            sleep(5);

            write(new_socket , hello , strlen(hello));
            printf("-------------Hello message sent---------------\n");
            close(new_socket);
                    
            return 0;   // 프로세스 종료
        
        }
    }
    return 0;
}