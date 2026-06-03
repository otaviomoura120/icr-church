FROM node:lts

WORKDIR /home/dev/icr-church/frontend

# Install dependencies based on the preferred package manager
COPY package.json yarn.lock* package-lock.json* pnpm-lock.yaml* ./
COPY . .

#RUN \
#  if [ -f yarn.lock ]; then yarn --frozen-lockfile; \
#  elif [ -f package-lock.json ]; then npm i; \
#  elif [ -f pnpm-lock.yaml ]; then yarn global add pnpm && pnpm i; \
#  # Allow install without lockfile, so example works even without Node.js installed locally
#  else echo "Warning: Lockfile not found. It is recommended to commit lockfiles to version control." && yarn install; \
#  fi
RUN npm install -g pnpm

#RUN yarn global add pnpm && pnpm i;

# Note: Don't expose ports here, Compose will handle that for us

# Start vue.js in development mode based on the preferred package manager
#CMD \
#  if [ -f yarn.lock ]; then yarn dev --host; \
#  elif [ -f package-lock.json ]; then npm run dev -- --host; \
#  elif [ -f pnpm-lock.yaml ]; then pnpm dev --host; \
#  else yarn dev --host; \
#  fi
CMD pnpm dev --host

ARG UID=501

RUN useradd -m -u ${UID} dev

RUN mkdir -p /home/dev/.ssh && \
    chmod 700 /home/dev/.ssh && \
    chown -R dev:dev /home/dev/.ssh

RUN mkdir -p /home/dev/.claude && \
    chown -R dev:dev /home/dev/.claude

USER dev

RUN ssh-keyscan github.com >> /home/dev/.ssh/known_hosts
RUN chmod 644 /home/dev/.ssh/known_hosts