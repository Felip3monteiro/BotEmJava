from pdf2docx import Converter

# Defina os caminhos dos arquivos
pdf_file = "src/main/python/webscraping/CurriculoFelipeMonteiroSilveira.pdf"
word_file = "documento.docx"

# Converte o PDF para Word
cv = Converter(pdf_file)
cv.convert(word_file, start=0, end=None)  # Converte todas as páginas
cv.close()

print("Conversão concluída!")