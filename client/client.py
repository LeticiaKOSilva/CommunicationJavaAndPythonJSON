import socket
import json
import tkinter as tk
import random

HOST = '127.0.0.1'  # Endereço do host correspondente
PORT = 14003  # Porta de Comunicação entre os sockets

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as socket_client:  # Criação do socket
    socket_client.connect((HOST, PORT))
    
    # Recebe o JSON inicial do servidor
    data = socket_client.recv(4096)
    values = data.decode()
    json_team = json.loads(values)
    #print(json_team)

    teams = json_team["Turmas"]

    lider = []

    for team in teams:
        nome_turma = team['nome']
        id_turma = team['id']
        estudantes = team['Estudantes']

        if estudantes:
            aluno_sorteado = random.choice(estudantes)
            lider.append({"turma": str(nome_turma + id_turma), "aluno_sorteado": aluno_sorteado['aluno']})

    # Agora você envia os dados de volta para o servidor
    json_lider = json.dumps(lider)
    #print(json_lider)
    socket_client.send(json_lider.encode('utf-8'))