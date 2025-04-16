-- Limpe as tabelas se já existirem dados
DELETE FROM thing_category;
DELETE FROM thing;
DELETE FROM account;
DELETE FROM category;
ALTER TABLE account ALTER COLUMN id RESTART WITH 1;
ALTER TABLE category ALTER COLUMN id RESTART WITH 1;
ALTER TABLE thing ALTER COLUMN id RESTART WITH 1;

-- Inserir Account (A senha é "Senha123" para todos e está criptografada em SHA256)
INSERT INTO account (date, photo, name, tel, email, password, birth, cpf, type, status) VALUES
(CURRENT_TIMESTAMP -1284, 'https://randomuser.me/api/portraits/women/94.jpg', 'Alice Silva', '11999990001', 'alice@email.com', 'c00357563669ed21c34e13687cad669038eb88a2831fc8109b40ddc62f63e934', '1990-05-15', '123.456.789-01', 'USER', 'ON'),
(CURRENT_TIMESTAMP -1472, 'https://randomuser.me/api/portraits/men/38.jpg', 'Bruno Souza', '11999990002', 'bruno@email.com', 'c00357563669ed21c34e13687cad669038eb88a2831fc8109b40ddc62f63e934', '1985-07-20', '987.654.321-02', 'OPERATOR', 'ON'),
(CURRENT_TIMESTAMP -1829, 'https://randomuser.me/api/portraits/women/67.jpg', 'Carla Mendes', '11999990003', 'carla@email.com', 'c00357563669ed21c34e13687cad669038eb88a2831fc8109b40ddc62f63e934', '1993-09-10', '456.789.123-03', 'ADMIN', 'ON'),
(CURRENT_TIMESTAMP -1979, 'https://randomuser.me/api/portraits/men/89.jpg', 'Clementino Sertan', '11999990004', 'clementino@email.com', 'c00357563669ed21c34e13687cad669038eb88a2831fc8109b40ddc62f63e934', '1984-12-14', '445.566.677-08', 'OPERATOR', 'OFF');

-- Inserir Categories
INSERT INTO category (name, description, status) VALUES
('Eletrônicos', 'Dispositivos eletrônicos', 'ON'),
('Móveis', 'Móveis para casa e escritório', 'ON'),
('Esportes', 'Equipamentos esportivos', 'ON'),
('Roupas', 'Vestimentas variadas', 'ON'),
('Livros', 'Livros e materiais de estudo', 'ON'),
('Automotivo', 'Itens para carros e motos', 'ON');

-- Inserir Things com relacionamento "um para muitos" com Account
INSERT INTO thing (date, name, description, location, photo, price, status, account_id) VALUES
(CURRENT_TIMESTAMP -190, 'Notebook Dell', 'Core i7, 16GB RAM', 'Escritório', 'https://picsum.photos/195', 4500.00, 'ON', 1),
(CURRENT_TIMESTAMP -180, 'Mesa de Madeira', 'Mesa rústica de jantar', 'Sala', 'https://picsum.photos/196', 1200.00, 'ON', 2),
(CURRENT_TIMESTAMP -170, 'Bicicleta MTB', 'Bicicleta aro 29', 'Garagem', 'https://picsum.photos/197', 2500.00, 'ON', 3),
(CURRENT_TIMESTAMP -160, 'Jaqueta de Couro', 'Jaqueta preta masculina', 'Guarda-roupa', 'https://picsum.photos/198', 350.00, 'ON', 1),
(CURRENT_TIMESTAMP -150, 'Livro de Java', 'Programação em Java', 'Estante', 'https://picsum.photos/199', 120.00, 'ON', 2),
(CURRENT_TIMESTAMP -140 , 'Cadeira Gamer', 'Cadeira ergonômica', 'Quarto', 'https://picsum.photos/200', 900.00, 'ON', 3),
(CURRENT_TIMESTAMP -130, 'Smartphone Samsung', 'Galaxy S21', 'Mesa', 'https://picsum.photos/201', 3500.00, 'ON', 1),
(CURRENT_TIMESTAMP -120, 'Capacete Moto', 'Capacete para motociclista', 'Garagem', 'https://picsum.photos/202', 400.00, 'ON', 2),
(CURRENT_TIMESTAMP -110, 'Raquete de Tênis', 'Raquete profissional', 'Escritório', 'https://picsum.photos/203', 800.00, 'ON', 3),
(CURRENT_TIMESTAMP -100, 'Monitor 4K', 'Monitor Ultra HD 27"', 'Escritório', 'https://picsum.photos/204', 2500.00, 'ON', 1);

-- Inserir relacionamento "muitos para muitos" entre Thing e Category
INSERT INTO thing_category (thing_id, category_id) VALUES
(1, 1), (1, 5),  -- Notebook → Eletrônicos, Livros
(2, 2),  -- Mesa → Móveis
(3, 3),  -- Bicicleta → Esportes
(4, 4),  -- Jaqueta → Roupas
(5, 5),  -- Livro de Java → Livros
(6, 2),  -- Cadeira Gamer → Móveis
(7, 1),  -- Smartphone → Eletrônicos
(8, 6),  -- Capacete → Automotivo
(9, 3),  -- Raquete → Esportes
(10, 1), (10, 2); -- Monitor → Eletrônicos, Móveis