import csv

name_in = 'rgbin.txt'
name_out = 'rgbout.csv'

fin = open(name_in, 'r')

# d = fin.read().split('.\n')
# print((d))

with open(name_out, mode='w', newline='') as f:
    fout = csv.writer(f, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
    cnt = 1
    for x in fin:
        d = x.split()
        # print(d[2] + " " + d[5] + " " + d[8])
        fout.writerow([cnt, d[2], d[5], d[8]])
        cnt += 1
    print("done")

fin.close()