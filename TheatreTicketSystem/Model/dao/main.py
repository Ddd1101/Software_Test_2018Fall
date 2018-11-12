import javalang
import unittest
import os

list = os.listdir("../dao")

for itor in list:
    filename = itor

f = open("ConsumeDAO.java","rb")
source = f.read()
f.close()

tree = javalang.parse.parse(source)

tmp = tree.types[0].body[1]

print(tree.types[0].body[1].body[0].condition.attrs[0] == 'operator')
print(tmp.body[1].condition.operator)