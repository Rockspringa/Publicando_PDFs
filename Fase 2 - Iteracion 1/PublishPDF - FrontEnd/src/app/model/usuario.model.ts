export class Usuario {
    private _username: string;
    private _password: string | null = null;
    private _nombre: string | null = null;
    private _descripcion: string | null = null;
    private _gustos: string | null = null;
    private _hobbies: string | null = null;
    private _foto: string | null = null;

    constructor(user: string, pass: string) {
        this._username = user;
        this._password = pass;
    }

    get username(): string { return this._username; }

    set username(user: string) { this._username = user; }

    get password(): string | null { return this._password; }

    set password(pass: string | null) { this._password = pass; }

    get nombre(): string | null { return this._nombre; }

    set nombre(name: string | null) { this._nombre = name; }

    get descripcion(): string | null { return this._descripcion; }

    set descripcion(desc: string | null) { this._descripcion = desc; }

    get gustos(): string | null { return this._gustos; }

    set gustos(gustos: string | null) { this._gustos = gustos; }

    get hobbies(): string | null { return this._hobbies; }

    set hobbies(hobbies: string | null) { this._hobbies = hobbies; }

    get foto(): string | null { return this._foto; }

    set foto(foto: string | null) { this._foto = foto; }

    get type(): string { return 'null'; }

}