import socket
import io
import serial
import time

ser = serial.Serial("/dev/ttyACM0", 115200)
ser.flushInput()

tcp_ip = '127.0.0.1'
# tcp_ip = '192.168.0.100'
tcp_port = 6666
BUFFER_SIZE = 1024
MESSAGE = "hello server\n"

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((tcp_ip, tcp_port))

s.send(MESSAGE.encode('UTF-8'))
data = s.recv(BUFFER_SIZE)
s.close()

print("received data:" + data.decode('utf-8'))
time_start = time.time()
while True:
    line = ""
    if (ser.in_waiting > 0):
        line = ser.readline()
        print(line)

    ser.write(b'2')
    time_end = time.time()
    print(time_end - time_start)
    # break
