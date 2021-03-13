import { ConfigWorkSlot } from './WorkSlotModel';

export class WorkSlotDay {
  name: string;
  workSlots: Array<ConfigWorkSlot> = [];

  constructor(name: string) {
    this.name = name;
  }
}
