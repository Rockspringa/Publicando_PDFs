import { UserType } from "./user-type.model";
import { Usuario } from "../usuario.model";
import { ClickleableOption } from "../html/option.model";

export class Administrador extends Usuario {

    private _anuncios: string[] = [];

    private readonly header = [
        new ClickleableOption('Mis Suscripciones', 'suscripciones'),
        new ClickleableOption('Buscar Revistas', 'buscar')
    ];

    private readonly menu = [
        new ClickleableOption('Mi perfil', 'profile'),
        new ClickleableOption('Cerrar sesion', '/login')
    ];

    get type() { return UserType.ADMINISTRADOR; }

    get headerOptions(): ClickleableOption[] {
        return this.header;
    }
    
    get menuOptions(): ClickleableOption[] {
        return this.menu;
    }

    get anuncios(): string[] { return this._anuncios; }

    set anuncios(anuncios: string[]) { this._anuncios = anuncios; }
    
}