insert into wallets (account_number, balance, currency)
values ('ACC-001', 10000.00, 'RUB')
on conflict (account_number) do nothing;

insert into wallets (account_number, balance, currency)
values ('ACC-002', 5000.00, 'RUB')
on conflict (account_number) do nothing;
