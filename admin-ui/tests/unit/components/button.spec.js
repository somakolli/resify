import { render } from '@testing-library/vue';

import Button from '@/components/Button.vue';

test('show slot', async () => {
  const { getByText } = render(Button, {
    slots: {
      default: 'Button',
    },
  });

  getByText('Button');
});
