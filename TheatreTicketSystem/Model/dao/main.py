import javalang
import unittest
import os

#get filename list
def get_file_list(file_dir):
    file_list = []
    file_names = os.listdir(file_dir)
    if (len(file_names)>0):
        for fn in file_names:
            file_list.append(fn)

    if(len(file_list)>0):
        file_list.sort()
    return file_list

#logical symbol mutation
def logical_symbol_mutation(node):
    has_condition = 0
    for i in node.attrs:
        if i == 'condition':
            has_condition = 1
    if has_condition == 1:
        if node.condition.attrs[0] == 'operator':
            if node.condition.operator == '==' :
                node.condition.operator = '!='
                print("== --> !=")
            elif node.condition.operator == '||' :
                node.condition.operator = '&&'
                print("|| --> &&")


#return statement mustation
#def statement_symbol_mutation(tree):
#    #do something

#operator the leaves
def do_mutation(node,mutation_type):
    has_sub_body = 0
    for sub in node.body:
        if (int(mutation_type)) == 1:
            logical_symbol_mutation(sub)
        for i in sub.attrs:
            if i == 'body':
                has_sub_body = 1
        if has_sub_body == 1 :
            do_mutation(sub,mutation_type)
        has_sub_body = 0


if __name__ == "__main__":
    list = get_file_list("../dao")

    for i in range(4,12,2):
        print(list[i])

    #key = input("输入文件编号：")
    key = '4'
    while key!='-1':
        filename = list[int(key)]
        print(filename)
        file = open(filename,"rb")
        source = file.read()
        file.close()
        tree = javalang.parse.parse(source)

        #mutation_type = input("请输入变异类型：\n"
        #                      "1、逻辑符号"
        #                      "2、return替换 \n")
        node = tree.types[0]
        do_mutation(node,1)

        tmp = tree.types[0]

        #print(tree.types[0].body[1].body[0].condition.attrs)
        #print(tree.types[0].body[1].body[0].condition.operator)

        file = open(key+".java", "wb")
        file.write(source)
        file.close()

        key = input("输入文件编号：")