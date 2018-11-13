import javalang
import unittest
import os
from javalang import ast
import javasphinx
import javasphinx.compiler as compiler

file = open('ConsumeDAO.java', "rb")
source = file.read()
file.close()
path = os.getcwd()+'/ConsumeDAO.java'
print(path)
compiler = compiler.JavadocRestCompiler(None, member_headers, parser)
ast = javalang.parse.parse(source)

for path, node in ast :
    print(path, node)