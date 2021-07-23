FROM golang
ADD main.go /root
WORKDIR /root
RUN go build -o /root/mainApp /root/main.go
CMD ["./mainApp"]