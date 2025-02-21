CREATE TABLE profissionais (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL CHECK (cargo IN ('DESENVOLVEDOR', 'DESIGNER', 'SUPORTE', 'TESTER')),
    nascimento DATE NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contatos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    profissional_id INT NOT NULL,
    CONSTRAINT fk_profissional FOREIGN KEY (profissional_id) REFERENCES profissionais (id) ON DELETE CASCADE
);
