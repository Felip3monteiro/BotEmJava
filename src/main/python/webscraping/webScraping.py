from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
import time

# Configura o WebDriver (Baixa o ChromeDriver automaticamente)
service = Service(ChromeDriverManager().install())
driver = webdriver.Chrome(service=service)

# Abre a página da FURIA no Draft5
url = "https://draft5.gg/equipe/330-FURIA"
driver.get(url)

# Espera um pouco para garantir que a página carregue completamente
time.sleep(5)

# Busca os elementos com os títulos das partidas futuras
games = driver.find_elements(By.CLASS_NAME, "match-card__title")

print("Próximos jogos da FURIA:")
for game in games:
    print(game.text.strip())

# Fecha o navegador após a coleta dos dados
driver.quit()